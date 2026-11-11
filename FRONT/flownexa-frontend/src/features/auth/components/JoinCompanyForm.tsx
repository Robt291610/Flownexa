import { useState } from 'react';
import type React from 'react';
import { Box, TextField, Button, Alert, CircularProgress, Grid, Typography } from '@mui/material';
import type { JoinCompanyRequest } from '../types/auth.types';

interface JoinCompanyFormProps {
  onSubmit: (payload: JoinCompanyRequest) => void;
  loading: boolean;
  error: string | null;
  submitted: boolean;
}

const EMPTY_FORM: JoinCompanyRequest = {
  companyName: '',
  fullName: '',
  email: '',
  password: '',
  userPhone: '',
  jobTitle: '',
};

export function JoinCompanyForm({ onSubmit, loading, error, submitted }: JoinCompanyFormProps) {
  const [form, setForm] = useState<JoinCompanyRequest>(EMPTY_FORM);

  const handleChange = (field: keyof JoinCompanyRequest) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm((prev) => ({ ...prev, [field]: e.target.value }));
  };

  const handleSubmit = (e: React.SyntheticEvent<HTMLFormElement>) => {
    e.preventDefault();
    onSubmit(form);
  };

  if (submitted) {
    return (
      <Alert severity="success" sx={{ borderRadius: 2 }}>
        Your request to join <strong>{form.companyName}</strong> has been sent. An admin will review it and you'll be notified once it's approved.
      </Alert>
    );
  }

  return (
    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ width: '100%' }}>
      {error && (
        <Alert severity="error" sx={{ mb: 2.5, borderRadius: 2 }}>
          {error}
        </Alert>
      )}

      <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
        Enter the exact name of the company you want to join. An admin from that company will need to approve your request.
      </Typography>

      <Grid container spacing={2}>
        <Grid size={{ xs: 12 }}>
          <TextField fullWidth label="Company name" value={form.companyName} onChange={handleChange('companyName')} required size="small" />
        </Grid>
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
        {loading ? <CircularProgress size={22} color="inherit" /> : 'Request to join'}
      </Button>
    </Box>
  );
}