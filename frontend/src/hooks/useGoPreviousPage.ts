import { useLocation, useNavigate } from 'react-router-dom';

const useGoPreviousPage = () => {
  const navigate = useNavigate();

  const { pathname } = useLocation();

  const goPreviousPage = () => {
    navigate(pathname.split('/').slice(0, -1).join('/'));
  };

  return { goPreviousPage };
};

export default useGoPreviousPage;
