import React, { useRef } from 'react';

import FloatingActionButton from '@/components/host/LandingView/FloatingActionButton';
import HeroSection from '@/components/host/LandingView/HeroSection';
import HostViewSection1 from '@/components/host/LandingView/HostViewSection1';
import HostViewSection2 from '@/components/host/LandingView/HostViewSection2';
import HostViewSection3 from '@/components/host/LandingView/HostViewSection3';
import UserViewSection1 from '@/components/host/LandingView/UserViewSection1';
import UserViewSection2 from '@/components/host/LandingView/UserViewSection2';
import UserViewSection3 from '@/components/host/LandingView/UserViewSection3';

import styles from './styles';

const Landing: React.FC = () => {
  const mainRef = useRef<HTMLDivElement>(null);

  return (
    <div css={styles.layout} ref={mainRef}>
      <HeroSection />
      <UserViewSection1 />
      <UserViewSection2 />
      <UserViewSection3 />
      <HostViewSection1 />
      <HostViewSection3 />
      <FloatingActionButton mainRef={mainRef} />
    </div>
  );
};

export default Landing;
