import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/auth.service';
import type{ LoginRequest, ApiErrorResponse } from '../types/auth.types';
import { useAuth } from '../context/AuthContext';

export function useLogin() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const { setUser } = useAuth();

  const login = async (payload: LoginRequest) => {
    setLoading(true);
    setError(null);

    try {
      const result = await authService.login(payload);
      setUser({ userId: result.userId, email: result.email 
      });
      navigate('/dashboard');
    } catch (err: any) {
      const apiError: ApiErrorResponse | undefined = err?.body;
      setError(apiError?.message ?? 'Invalid email or password');
    } finally {
      setLoading(false);
    }
  };

  return { login, loading, error };
}