import { useState } from 'react';
import type React from 'react';
import { Box, TextField, Button, Alert, CircularProgress, Grid, Divider, Typography } from '@mui/material';
import type { CreateCompanyRequest } from '../types/auth.types';

interface CreateCompanyFormProps {
  onSubmit: (payload: CreateCompanyRequest) => void;
  loading: boolean;
  error: string | null;
}

const EMPTY_FORM: CreateCompanyRequest = {
  companyName: '',
  legalName: '',
  taxId: '',
  industry: '',
  country: '',
  companyPhone: '',
  contactEmail: '',
  fullName: '',
  email: '',
  password: '',
  userPhone: '',
  jobTitle: '',
};

export function CreateCompanyForm({ onSubmit, loading, error }: CreateCompanyFormProps) {
  const [form, setForm] = useState<CreateCompanyRequest>(EMPTY_FORM);

  const handleChange = (field: keyof CreateCompanyRequest) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm((prev) => ({ ...prev, [field]: e.target.value }));
  };

  const handleSubmit = (e: React.SyntheticEvent<HTMLFormElement>) => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ width: '100%' }}>
      {error && (
        <Alert severity="error" sx={{ mb: 2.5, borderRadius: 2 }}>
          {error}
        </Alert>
      )}

      <Typography variant="subtitle2" sx={{ fontWeight: 700, mb: 1 }}>
        Company details
      </Typography>

      <Grid container spacing={2}>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Company name" value={form.companyName} onChange={handleChange('companyName')} required size="small" />
        </Grid>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Legal name" value={form.legalName} onChange={handleChange('legalName')} required size="small" />
        </Grid>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Tax ID" value={form.taxId} onChange={handleChange('taxId')} required size="small" />
        </Grid>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Country" value={form.country} onChange={handleChange('country')} required size="small" />
        </Grid>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Industry" value={form.industry} onChange={handleChange('industry')} size="small" />
        </Grid>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Company phone" value={form.companyPhone} onChange={handleChange('companyPhone')} size="small" />
        </Grid>
        <Grid size={{ xs: 12 }}>
          <TextField fullWidth label="Contact email" type="email" value={form.contactEmail} onChange={handleChange('contactEmail')} size="small" />
        </Grid>
      </Grid>

      <Divider sx={{ my: 3 }} />

      <Typography variant="subtitle2" sx={{ fontWeight: 700, mb: 1 }}>
        Your admin account
      </Typography>

      <Grid container spacing={2}>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Full name" value={form.fullName} onChange={handleChange('fullName')} required size="small" />
        </Grid>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Job title" value={form.jobTitle} onChange={handleChange('jobTitle')} size="small" />
        </Grid>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Work email" type="email" value={form.email} onChange={handleChange('email')} required size="small" />
        </Grid>
        <Grid size={{ xs: 12, sm: 6 }}>
          <TextField fullWidth label="Phone" value={form.userPhone} onChange={handleChange('userPhone')} size="small" />
        </Grid>
        <Grid size={{ xs: 12 }}>
          <TextField
            fullWidth
            label="Password"
            type="password"
            value={form.password}
            onChange={handleChange('password')}
            required
            size="small"
            helperText="At least 8 characters"
          />
        </Grid>
      </Grid>

      <Button
        type="submit"
        fullWidth
        variant="contained"
        size="large"
        disabled={loading}
        sx={{ py: 1.3, borderRadius: 2, textTransform: 'none', fontWeight: 600, fontSize: '1rem', boxShadow: 'none', mt: 3 }}
      >
        {loading ? <CircularProgress size={22} color="inherit" /> : 'Create company'}
      </Button>
    </Box>
  );
}