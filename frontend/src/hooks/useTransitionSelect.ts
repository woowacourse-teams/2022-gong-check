import { useLocation } from 'react-router-dom';

const useTransitionSelect = () => {
  const location = useLocation();

  const previousPath = sessionStorage.getItem('path');

  const pathLength = location.pathname.split('/').length;
  const previousPathLength = previousPath?.split('/').length;
  const isPwdPage = location.pathname.split('/')[3] == 'pwd';
  const isPreviousPwdPage = previousPath?.split('/')[3] == 'pwd';

  sessionStorage.setItem('path', location.pathname);

  if (pathLength === 4 && isPwdPage) {
    return '';
  }
  if (pathLength === 4) {
    if (isPreviousPwdPage) return '';
    return 'slide-left';
  }
  if (pathLength === 5) {
    if (previousPathLength === 4 && !isPreviousPwdPage) {
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
