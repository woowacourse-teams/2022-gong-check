import SpaceList from './pages/SpaceList';
import JobList from './pages/JobList';
import TaskList from './pages/TaskList';
import { Outlet } from 'react-router-dom';

const DefaultLayout = () => {
  return (
    <>
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
