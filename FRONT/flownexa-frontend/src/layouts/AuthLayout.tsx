import type { ReactNode } from 'react';
import { Box, Grid, Typography, Stack, useMediaQuery, useTheme } from '@mui/material';
import loginIllustration from '../assets/illustrations/login-illustration.png';
import logo from '../assets/logo/flownexa-logo.png';

interface AuthLayoutProps {
  title: string;
  subtitle: string;
  children: ReactNode;
}

export function AuthLayout({ title, subtitle, children }: AuthLayoutProps) {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));

  return (
    <Grid container sx={{ minHeight: '100vh' }}>
      {/* Left branding panel — hidden below md */}
      <Grid
        size={{ xs: 12, md: 5, lg: 6 }}
        sx={{
          display: { xs: 'none', md: 'flex' },
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
          background: 'linear-gradient(160deg, #1e3a8a 0%, #2563eb 55%, #3b82f6 100%)',
          color: '#fff',
          p: { md: 4, lg: 6 },
          position: 'relative',
          overflow: 'hidden',
        }}
      >
        <Stack spacing={3} sx={{ maxWidth: 440, width: '100%', zIndex: 1 }}>
          <Stack direction="row" spacing={1.2} sx={{ alignItems: 'center' }}>
            <Box
              component="img"
              src={logo}
              alt="Flownexa logo"
              sx={{ width: { md: 36, lg: 40 }, height: { md: 36, lg: 40 }, borderRadius: 1.5 }}
            />
            <Typography variant="h5" sx={{ fontWeight: 700 }}>
              Flownexa
            </Typography>
          </Stack>

          <Typography
            sx={{
              fontWeight: 700,
              lineHeight: 1.3,
              fontSize: { md: '1.6rem', lg: '2.125rem' },
            }}
          >
            Approvals, workflows, and audits — all in one place.
          </Typography>

          <Typography
            variant="body1"
            sx={{ color: 'rgba(255,255,255,0.85)', fontSize: { md: '0.95rem', lg: '1rem' } }}
          >
            Automate purchase requests, time-off, and expense approvals with
            full traceability and role-based control across your organization.
          </Typography>

          <Box
            component="img"
            src={loginIllustration}
            alt="Approval workflow illustration"
            sx={{
              width: '100%',
              maxWidth: { md: 280, lg: 380 },
              mt: 2,
              alignSelf: 'center',
            }}
          />
        </Stack>
      </Grid>

      {/* Right content panel — full width on mobile */}
      <Grid
        size={{ xs: 12, md: 7, lg: 6 }}
        sx={{
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'center',
          alignItems: 'center',
          bgcolor: '#f8fafc',
          p: { xs: 3, sm: 5, md: 6 },
          minHeight: '100vh',
        }}
      >
        <Box sx={{ width: '100%', maxWidth: 400 }}>
          {isMobile && (
            <Stack
              direction="row"
              spacing={1}
              sx={{ alignItems: 'center', justifyContent: 'center', mb: 4 }}
            >
              <Box
                component="img"
                src={logo}
                alt="Flownexa logo"
                sx={{ width: 32, height: 32, borderRadius: 1 }}
              />
              <Typography variant="h6" sx={{ fontWeight: 700, color: 'primary.main' }}>
                Flownexa
              </Typography>
            </Stack>
          )}

          <Typography
            variant="h5"
            sx={{ fontWeight: 700, textAlign: { xs: 'center', md: 'left' } }}
            gutterBottom
          >
            {title}
          </Typography>

          <Typography
            variant="body2"
            color="text.secondary"
            sx={{ mb: 4, textAlign: { xs: 'center', md: 'left' } }}
          >
            {subtitle}
          </Typography>

          {children}
        </Box>
      </Grid>
    </Grid>
  );
}