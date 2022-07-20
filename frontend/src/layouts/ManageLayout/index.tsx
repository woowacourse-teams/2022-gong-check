import { Suspense } from 'react';
import { useQuery } from 'react-query';
import { Outlet } from 'react-router-dom';

import Navigation from '@/components/host/Navigation';

import apis from '@/apis';

const ManageLayout = () => {
  const { data } = useQuery(['spaces'], apis.getSpaces, { suspense: true });

  return (
    <>
      <Navigation spaces={data?.spaces} />
      <Outlet />
    </>
  );
};

export default ManageLayout;
