import { lazy } from 'react';

import DefaultLayout from '@/layouts/DefaultLayout';
import HostLayout from '@/layouts/HostLayout';
import ManageLayout from '@/layouts/ManageLayout';
import UserLayout from '@/layouts/UserLayout';

// user
const SpaceListPage = lazy(() => import('@/pages/user/SpaceList'));
const JobListPage = lazy(() => import('@/pages/user/JobList'));
const TaskListPage = lazy(() => import('@/pages/user/TaskList'));
const PasswordPage = lazy(() => import('@/pages/user/Password'));

// host
const HomePage = lazy(() => import('@/pages/host/Home'));
const AuthCallBackPage = lazy(() => import('@/pages/host/AuthCallBack'));
const DashBoardPage = lazy(() => import('@/pages/host/DashBoard'));
const SpaceCreatePage = lazy(() => import('@/pages/host/SpaceCreate'));
const SpaceUpdatePage = lazy(() => import('@/pages/host/SpaceUpdate'));
const SpaceRecordPage = lazy(() => import('@/pages/host/SpaceRecord'));
const JobCreatePage = lazy(() => import('@/pages/host/JobCreate'));
const JobUpdatePage = lazy(() => import('@/pages/host/JobUpdate'));
const PasswordUpdatePage = lazy(() => import('@/pages/host/PasswordUpdate'));

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
            path: 'pwd',
            element: <PasswordPage />,
          },
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
            path: '',
            element: <HomePage />,
          },
          {
            path: 'authCallback',
            element: <AuthCallBackPage />,
          },
          {
            path: 'manage',
            element: <ManageLayout />,
            children: [
              {
                path: 'passwordUpdate',
                element: <PasswordUpdatePage />,
              },
              {
                path: 'spaceCreate',
                element: <SpaceCreatePage />,
              },
              {
                path: ':spaceId',
                element: <DashBoardPage />,
              },
              {
                path: ':spaceId/spaceUpdate',
                element: <SpaceUpdatePage />,
              },
              {
                path: ':spaceId/spaceRecord',
                element: <SpaceRecordPage />,
              },
              {
                path: ':spaceId/jobCreate',
                element: <JobCreatePage />,
              },
              {
                path: ':spaceId/jobUpdate/:jobId',
                element: <JobUpdatePage />,
              },
            ],
          },
        ],
      },
    ],
  },
  {
    path: '*',
    element: <div>잘못된 접근입니다.</div>,
  },
];

export default routes;
