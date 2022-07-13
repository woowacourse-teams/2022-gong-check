import { lazy } from 'react';

import DefaultLayout from '@/layouts/DefaultLayout';
import HostLayout from '@/layouts/HostLayout';
import UserLayout from '@/layouts/UserLayout';

const SpaceListPage = lazy(() => import('@/pages/user/SpaceList'));
const JobListPage = lazy(() => import('@/pages/user/JobList'));
const TaskListPage = lazy(() => import('@/pages/user/TaskList'));

const LoginPage = lazy(() => import('@/pages/host/Login'));
const ManagePage = lazy(() => import('@/pages/host/Manage'));

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
      {
        path: 'host',
        element: <HostLayout />,
        children: [
          {
            path: 'login',
            element: <LoginPage />,
          },
          {
            path: 'manage',
            element: <ManagePage />,
          },
        ],
      },
    ],
  },
];

export default routes;
