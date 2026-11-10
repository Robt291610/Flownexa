import { RouterProvider } from 'react-router-dom';
import { router } from './router';
import { ThemeProvider, CssBaseline } from '@mui/material';
import { AuthProvider } from '../features/auth/context/AuthContext';
import { theme } from './theme';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App
