import { css } from '@emotion/react';
import React, { useRef } from 'react';

import FloatingActionButton from '@/components/host/LandingView/FloatingActionButton';
import HeroSection from '@/components/host/LandingView/HeroSection';
import HostViewSection1 from '@/components/host/LandingView/HostViewSection1';
import HostViewSection2 from '@/components/host/LandingView/HostViewSection2';
import HostViewSection3 from '@/components/host/LandingView/HostViewSection3';
import UserViewSection1 from '@/components/host/LandingView/UserViewSection1';
import UserViewSection2 from '@/components/host/LandingView/UserViewSection2';
import UserViewSection3 from '@/components/host/LandingView/UserViewSection3';

import useResizeScreen from '@/hooks/useResizeScreen';

import theme from '@/styles/theme';

const Landing: React.FC = () => {
  const mainRef = useRef<HTMLDivElement>(null);

  const { screenMode } = useResizeScreen();

  return (
    <div
      css={css`
        width: 100vw;
        background-color: ${theme.colors.background};
      `}
      ref={mainRef}
    >
      <HeroSection />
      <UserViewSection1 screenMode={screenMode} />
      <UserViewSection2 screenMode={screenMode} />
      <UserViewSection3 screenMode={screenMode} />
      <HostViewSection1 screenMode={screenMode} />
      <HostViewSection2 screenMode={screenMode} />
      <HostViewSection3 screenMode={screenMode} />
      <FloatingActionButton mainRef={mainRef} />
    </div>
  );
};

export default Landing;
