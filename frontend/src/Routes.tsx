import { lazy } from 'react';

import DefaultLayout from '@/layouts/DefaultLayout';
import UserLayout from '@/layouts/UserLayout';

const SpaceListPage = lazy(() => import('@/pages/SpaceList'));
const JobListPage = lazy(() => import('@/pages/JobList'));
const TaskListPage = lazy(() => import('@/pages/TaskList'));

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
            element: <SpaceListPage />,
          },
          {
            path: 'spaces/:spaceId',
            element: <JobListPage />,
          },
          {
            path: 'spaces/:spaceId/:jobId',
            element: <TaskListPage />,
          },
        ],
      },
    ],
  },
];

export default routes;
