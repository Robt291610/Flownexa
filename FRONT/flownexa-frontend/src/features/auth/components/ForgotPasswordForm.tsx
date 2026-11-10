import { useState } from 'react';
import type React from 'react';
import { Box, TextField, Button, Alert, CircularProgress, InputAdornment, Typography, Link as MuiLink } from '@mui/material';
import EmailOutlinedIcon from '@mui/icons-material/EmailOutlined';
import { Link as RouterLink } from 'react-router-dom';

interface ForgotPasswordFormProps {
  onSubmit: (email: string) => void;
  loading: boolean;
  error: string | null;
  submitted: boolean;
}

export function ForgotPasswordForm({ onSubmit, loading, error, submitted }: ForgotPasswordFormProps) {
  const [email, setEmail] = useState('');

  const handleSubmit = (e: React.SyntheticEvent<HTMLFormElement>) => {
    e.preventDefault();
    onSubmit(email);
  };

  if (submitted) {
    return (
      <Box>
        <Alert severity="success" sx={{ borderRadius: 2, mb: 2 }}>
          If an account exists for <strong>{email}</strong>, a password reset link has been sent.
        </Alert>
        <Typography variant="body2" color="text.secondary">
          Didn't get it? Check your spam folder, or{' '}
          <MuiLink component={RouterLink} to="/login" underline="hover">
            return to sign in
          </MuiLink>
          .
        </Typography>
      </Box>
    );
  }

  return (
    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ width: '100%' }}>
      {error && (
        <Alert severity="error" sx={{ mb: 2.5, borderRadius: 2 }}>
          {error}
        </Alert>
      )}

      <TextField
        fullWidth
        id="email"
        label="Work email"
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
        autoComplete="email"
        margin="normal"
        placeholder="you@company.com"
        slotProps={{
          input: {
            startAdornment: (
              <InputAdornment position="start">
                <EmailOutlinedIcon fontSize="small" color="action" />
              </InputAdornment>
            ),
          },
        }}
      />

      <Button
        type="submit"
        fullWidth
        variant="contained"
        size="large"
        disabled={loading}
        sx={{ py: 1.3, borderRadius: 2, textTransform: 'none', fontWeight: 600, fontSize: '1rem', boxShadow: 'none', mt: 1 }}
      >
        {loading ? <CircularProgress size={22} color="inherit" /> : 'Send reset link'}
      </Button>

      <Box sx={{ textAlign: 'center', mt: 3 }}>
        <MuiLink component={RouterLink} to="/login" variant="body2" underline="hover">
          Back to sign in
        </MuiLink>
      </Box>
    </Box>
  );
}