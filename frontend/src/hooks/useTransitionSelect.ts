import { useLocation } from 'react-router-dom';

const getPageByPath = (pathname: string) => {
  const pathList = pathname.split('/');

  if (pathList.length === 4 && pathList[3] === 'pwd') return 'passwordPage';
  if (pathList.length === 4 && pathList[3] === 'spaces') return 'spaceListPage';
  if (pathList.length === 5) return 'jobListPage';
  if (pathList.length === 6) return 'taskListPage';

  return '';
};

const useTransitionSelect = () => {
  const location = useLocation();
  const previousPath = sessionStorage.getItem('path');

  const previousPage = getPageByPath(previousPath || '');
  const currentPage = getPageByPath(location.pathname);

  sessionStorage.setItem('path', location.pathname);

  if (currentPage === 'passwordPage') {
    return '';
  }
  if (currentPage === 'spaceListPage') {
    if (previousPage === 'passwordPage') return '';
    return 'slide-left';
  }
  if (currentPage === 'jobListPage') {
    if (previousPage === 'spaceListPage') {
      return 'slide-right';
    }
    if (previousPage === 'taskListPage') {
      return 'left';
    }
  }
  if (currentPage === 'taskListPage') {
    return 'right';
  }

  return '';
};

export default useTransitionSelect;
