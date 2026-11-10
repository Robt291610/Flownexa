import { useState } from 'react';
import type React from 'react';
import {
  Box,
  TextField,
  Button,
  Alert,
  CircularProgress,
  InputAdornment,
  IconButton,
  Typography,
  Link as MuiLink,
} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { Link as RouterLink } from 'react-router-dom';

interface ResetPasswordFormProps {
  onSubmit: (newPassword: string) => void;
  loading: boolean;
  error: string | null;
  success: boolean;
}

export function ResetPasswordForm({ onSubmit, loading, error, success }: ResetPasswordFormProps) {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [mismatch, setMismatch] = useState(false);

  const handleSubmit = (e: React.SyntheticEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setMismatch(true);
      return;
    }

    setMismatch(false);
    onSubmit(password);
  };

  if (success) {
    return (
      <Alert severity="success" sx={{ borderRadius: 2 }}>
        Your password has been reset successfully. Redirecting you to sign in...
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

      {mismatch && (
        <Alert severity="warning" sx={{ mb: 2.5, borderRadius: 2 }}>
          Passwords do not match.
        </Alert>
      )}

      <TextField
        fullWidth
        id="password"
        label="New password"
        type={showPassword ? 'text' : 'password'}
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
        autoComplete="new-password"
        margin="normal"
        helperText="At least 8 characters"
        slotProps={{
          input: {
            startAdornment: (
              <InputAdornment position="start">
                <LockOutlinedIcon fontSize="small" color="action" />
              </InputAdornment>
            ),
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={() => setShowPassword((prev) => !prev)}
                  edge="end"
                  size="small"
                >
                  {showPassword ? <VisibilityOff fontSize="small" /> : <Visibility fontSize="small" />}
                </IconButton>
              </InputAdornment>
            ),
          },
        }}
      />

      <TextField
        fullWidth
        id="confirmPassword"
        label="Confirm new password"
        type={showPassword ? 'text' : 'password'}
        value={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
        required
        autoComplete="new-password"
        margin="normal"
        slotProps={{
          input: {
            startAdornment: (
              <InputAdornment position="start">
                <LockOutlinedIcon fontSize="small" color="action" />
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
        {loading ? <CircularProgress size={22} color="inherit" /> : 'Reset password'}
      </Button>

      <Box sx={{ textAlign: 'center', mt: 3 }}>
        <Typography variant="body2" color="text.secondary" component="span">
          Remembered your password?{' '}
        </Typography>
        <MuiLink component={RouterLink} to="/login" variant="body2" underline="hover">
          Sign in
        </MuiLink>
      </Box>
    </Box>
  );
}