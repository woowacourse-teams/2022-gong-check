import { useQuery } from 'react-query';
import { Outlet } from 'react-router-dom';

import Navigation from '@/components/host/Navigation';

import apiSpace from '@/apis/space';

const ManageLayout = () => {
  const { data } = useQuery(['spaces'], apiSpace.getSpaces, { suspense: true });

  return (
    <>
      <Navigation spaces={data?.spaces} />
      <Outlet />
    </>
  );
};

export default ManageLayout;
