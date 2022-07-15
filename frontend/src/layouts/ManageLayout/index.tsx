import { Outlet } from 'react-router-dom';

import Navigation from '@/components/Navigation';

const ManageLayout = () => {
  return (
    <>
      <Navigation />
      <Outlet />
    </>
  );
};

export default ManageLayout;
