import { Outlet } from 'react-router-dom';

const DefaultLayout: React.FC = () => {
  return (
    <>
      <Outlet />
    </>
  );
};

export default DefaultLayout;
