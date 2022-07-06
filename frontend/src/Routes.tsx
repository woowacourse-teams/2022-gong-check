import Header from './components/Header';
import JobList from './pages/JobList';
import SpaceList from './pages/SpaceList';
import TaskList from './pages/TaskList';
import { Outlet } from 'react-router-dom';

const DefaultLayout = () => {
  return (
    <>
      <Header />
      <Outlet />
    </>
  );
};

const routes = [
  {
    path: '/',
    element: <DefaultLayout />,
    children: [
      {
        path: ':hostId',
        children: [
          {
            path: 'spaces',
            element: <SpaceList />,
          },
          {
            path: 'spaces/:spaceId',
            element: <JobList />,
          },
          {
            path: 'spaces/:spaceId/:jobId',
            element: <TaskList />,
          },
        ],
      },
    ],
  },
];

export default routes;
