import UserLayout from './layouts/UserLayout';

import JobList from '@/pages/JobList';
import SpaceList from '@/pages/SpaceList';
import TaskList from '@/pages/TaskList';

import DefaultLayout from '@/layouts/DefaultLayout';

const routes = [
  {
    path: '/',
    element: <DefaultLayout />,
    children: [
      {
        path: 'enter/:hostId',
        element: <UserLayout />,
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
