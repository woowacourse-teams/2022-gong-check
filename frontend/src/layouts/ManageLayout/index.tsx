import { Outlet } from 'react-router-dom';

import Navigation from '@/components/host/Navigation';

const ManageLayout = () => {
  return (
    <>
      <Navigation />
      <Outlet />
    </>
  );
};

export default ManageLayout;
