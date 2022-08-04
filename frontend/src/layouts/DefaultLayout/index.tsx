import { useEffect } from 'react';
import { Outlet } from 'react-router-dom';

import useModal from '@/hooks/useModal';

const DefaultLayout: React.FC = () => {
  const { closeModal } = useModal();

  useEffect(() => {
    closeModal();
  }, []);

  return (
    <>
      <Outlet />
    </>
  );
};

export default DefaultLayout;
