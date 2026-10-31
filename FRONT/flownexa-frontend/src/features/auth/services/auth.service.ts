import type { LoginRequest, LoginResponse, MeResponse } from '../types/auth.types';
import { apiClient } from './client';


export const authService = {
  async login(payload: LoginRequest): Promise<LoginResponse> {
    return apiClient.post<LoginResponse>('/api/auth/login', payload);
  },
  logout(): Promise<void> {
    return apiClient.post<void>('/api/auth/logout');
  },

  me(): Promise<MeResponse> {
    return apiClient.get<MeResponse>('/api/auth/me');
  },
};