import { useState } from 'react';
import type { SyntheticEvent } from 'react';
import { Box, Tabs, Tab, Typography, Link as MuiLink } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { AuthLayout } from '../../../layouts/AuthLayout';
import { CreateCompanyForm } from '../components/CreateCompanyForm';
import { JoinCompanyForm } from '../components/JoinCompanyForm';
import { useCreateCompany } from '../hooks/useCreateCompany';
import { useJoinCompany } from '../hooks/useJoinCompany';

export function RegisterPage() {
  const [tab, setTab] = useState(0);
  const createCompany = useCreateCompany();
  const joinCompany = useJoinCompany();

  const handleTabChange = (_: SyntheticEvent, newValue: number) => {
    setTab(newValue);
  };

  return (
    <AuthLayout
      title="Create your account"
      subtitle="Set up a new company or request to join an existing one."
    >
      <Tabs value={tab} onChange={handleTabChange} sx={{ mb: 3 }} variant="fullWidth">
        <Tab label="New company" />
        <Tab label="Join a company" />
      </Tabs>

      {tab === 0 && (
        <CreateCompanyForm
          onSubmit={createCompany.createCompany}
          loading={createCompany.loading}
          error={createCompany.error}
        />
      )}

      {tab === 1 && (
        <JoinCompanyForm
          onSubmit={joinCompany.joinCompany}
          loading={joinCompany.loading}
          error={joinCompany.error}
          submitted={joinCompany.submitted}
        />
      )}

      <Box sx={{ textAlign: 'center', mt: 3 }}>
        <Typography variant="body2" color="text.secondary" component="span">
          Already have an account?{' '}
        </Typography>
        <MuiLink component={RouterLink} to="/login" variant="body2" underline="hover">
          Sign in
        </MuiLink>
      </Box>
    </AuthLayout>
  );
}