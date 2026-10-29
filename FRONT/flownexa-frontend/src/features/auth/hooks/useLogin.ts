import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/auth.service';
import type{ LoginRequest, ApiErrorResponse } from '../types/auth.types';
import { tokenStorage } from '../services/storage/tokenStorage';

export function useLogin() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const login = async (payload: LoginRequest) => {
    setLoading(true);
    setError(null);

    try {
      const result = await authService.login(payload);
      tokenStorage.setToken(result.accessToken);
      navigate('/dashboard');
    } catch (err: any) {
      const apiError: ApiErrorResponse | undefined = err?.response?.data;
      setError(apiError?.message ?? 'Invalid email or password');
    } finally {
      setLoading(false);
    }
  };

  return { login, loading, error };
}