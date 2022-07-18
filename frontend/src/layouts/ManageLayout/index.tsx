import { Suspense } from 'react';
import { useQuery } from 'react-query';
import { Outlet } from 'react-router-dom';

import Navigation from '@/components/Navigation';

import apis from '@/apis';

const ManageLayout = () => {
  const { data } = useQuery(['spaces'], apis.getSpaces, { suspense: true });

  return (
    <Suspense fallback={<div>관리자 ManageLayout 로딩 스피너</div>}>
      <Navigation spaces={data?.spaces} />
      <Outlet />
    </Suspense>
  );
};

export default ManageLayout;
