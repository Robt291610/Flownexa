import { Box, Grid, Typography, Stack } from '@mui/material';
import { LoginForm } from '../components/LoginForm';
import { useLogin } from '../hooks/useLogin';
import loginIllustration from '../../../assets/illustrations/login-illustration.png';
import { Logo } from '../components/common/Logo';

export function LoginPage() {
  const { login, loading, error } = useLogin();

  const handleSubmit = (email: string, password: string) => {
    login({ email, password });
  };

  return (
    <Grid container sx={{ minHeight: '100vh' }}>
      {/* Left branding panel */}
      <Grid
        size={{ xs: 0, md: 6 }}
        sx={{
          display: { xs: 'none', md: 'flex' },
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
          background: 'linear-gradient(160deg, #1e3a8a 0%, #2563eb 55%, #3b82f6 100%)',
          color: '#fff',
          p: 6,
          position: 'relative',
          overflow: 'hidden',
        }}
      >
        <Stack spacing={3} sx={{ maxWidth: 440, zIndex: 1 }}>
          <Stack direction="row" spacing={1.2} alignItems="center">
            <Logo fontSize="large" />
            <Typography variant="h5" fontWeight={700}>
              Flownexa
            </Typography>
          </Stack>

          <Typography variant="h4" fontWeight={700} sx={{ lineHeight: 1.3 }}>
            Approvals, workflows, and audits — all in one place.
          </Typography>

          <Typography variant="body1" sx={{ color: 'rgba(255,255,255,0.85)' }}>
            Automate purchase requests, time-off, and expense approvals with
            full traceability and role-based control across your organization.
          </Typography>

          <Box
            component="img"
            src={loginIllustration}
            alt="Approval workflow illustration"
            sx={{ width: '100%', maxWidth: 380, mt: 2, alignSelf: 'center' }}
          />
        </Stack>
      </Grid>

      {/* Right form panel */}
      <Grid
        size={{ xs: 12, md: 6 }}
        sx={{
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
          bgcolor: '#f8fafc',
          p: { xs: 3, sm: 6 },
        }}
      >
        <Box sx={{ width: '100%', maxWidth: 400 }}>
          <Typography variant="h5" fontWeight={700} gutterBottom>
            Welcome back
          </Typography>
          <Typography variant="body2" color="text.secondary" sx={{ mb: 4 }}>
            Sign in to manage your organization's workflows and approvals.
          </Typography>

          <LoginForm onSubmit={handleSubmit} loading={loading} error={error} />
        </Box>
      </Grid>
    </Grid>
  );
}