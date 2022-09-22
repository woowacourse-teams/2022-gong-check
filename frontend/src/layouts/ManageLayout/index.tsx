import isNull from '@/utils/isNull';
import { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';

import LeftNavigator from '@/components/host/Navigator/LeftNavigator';
import TopNavigator from '@/components/host/Navigator/TopNavigator';

const ManageLayout = () => {
  const [isDeskTop, setIsDeskTop] = useState<boolean | null>(null);

  const resizeHandler = () => {
    if (innerWidth >= 1024) {
      setIsDeskTop(true);
      return;
    }
    setIsDeskTop(false);
  };

  useEffect(() => {
    resizeHandler();

    window.addEventListener('resize', resizeHandler);

    return () => {
      window.removeEventListener('resize', resizeHandler);
    };
  }, []);

  return (
    <>
      {!isNull(isDeskTop) && (isDeskTop ? <LeftNavigator /> : <TopNavigator />)}
      <Outlet />
    </>
  );
};

export default ManageLayout;
