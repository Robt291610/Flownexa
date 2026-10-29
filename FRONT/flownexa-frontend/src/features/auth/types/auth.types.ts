export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  userId: string;
  email: string;
  accessToken: string;
}

export interface ApiErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message?: string;
  path?: string;
}