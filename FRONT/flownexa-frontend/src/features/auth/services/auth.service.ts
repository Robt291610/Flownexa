import type { LoginRequest, LoginResponse } from '../types/auth.types';
import { apiClient } from './client';


export const authService = {
  async login(payload: LoginRequest): Promise<LoginResponse> {
    return apiClient.post<LoginResponse>('/api/auth/login', payload);
  },
};