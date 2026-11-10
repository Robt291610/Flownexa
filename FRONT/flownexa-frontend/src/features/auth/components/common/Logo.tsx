import { SvgIcon } from '@mui/material';
import type { SvgIconProps } from '@mui/material';

export function Logo(props: SvgIconProps) {
  return (
    <SvgIcon {...props} viewBox="0 0 48 48">
      <circle cx="24" cy="24" r="22" fill="currentColor" opacity="0.15" />
      <path
        d="M24 10c-7.73 0-14 6.27-14 14s6.27 14 14 14 14-6.27 14-14-6.27-14-14-14zm0 4c5.52 0 10 4.48 10 10s-4.48 10-10 10-10-4.48-10-10 4.48-10 10-10z"
        fill="currentColor"
      />
      <path
        d="M20.5 24.5l2.5 2.5 5-5"
        stroke="currentColor"
        strokeWidth="2.2"
        strokeLinecap="round"
        strokeLinejoin="round"
        fill="none"
      />
    </SvgIcon>
  );
}