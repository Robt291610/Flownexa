export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  userId: string;
  email: string;
}

export interface MeResponse {
  userId: string;
  email: string;
}

export interface ForgotPasswordRequest {
  email: string;
}

export interface ResetPasswordRequest {
  token: string;
  newPassword: string;
}

export interface CreateCompanyRequest {
  companyName: string;
  legalName: string;
  taxId: string;
  industry?: string;
  country: string;
  companyPhone?: string;
  contactEmail?: string;
  fullName: string;
  email: string;
  password: string;
  userPhone?: string;
  jobTitle?: string;
}

export interface JoinCompanyRequest {
  companyName: string;
  fullName: string;
  email: string;
  password: string;
  userPhone?: string;
  jobTitle?: string;
}

export interface RegisterCompanyResponse {
  organizationId: string;
  companyName: string;
  userId: string;
  email: string;
  status: 'ACTIVE' | 'PENDING' | 'REJECTED' | 'INACTIVE';
}

export interface ApiErrorResponse {
  timestamp?: string;
  status?: number;
  error?: string;
  message?: string;
  path?: string;
}