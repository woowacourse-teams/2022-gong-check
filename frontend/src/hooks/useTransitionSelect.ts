import { useLocation } from 'react-router-dom';

const useTransitionSelect = () => {
  const location = useLocation();

  const previousPath = sessionStorage.getItem('path');

  const pathLength = location.pathname.split('/').length;
  const previousPathLength = previousPath?.split('/').length;

  sessionStorage.setItem('path', location.pathname);

  if (pathLength === 4) {
    return 'slide-left';
  }
  if (pathLength === 5) {
    if (previousPathLength === 4) {
      return 'slide-right';
    }
    if (previousPathLength === 6) {
      return 'left';
    }
  }
  if (pathLength === 6) {
    return 'right';
  }

  return '';
};

export default useTransitionSelect;
