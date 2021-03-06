import { lazy } from 'react';

import DefaultLayout from '@/layouts/DefaultLayout';
import HostLayout from '@/layouts/HostLayout';
import ManageLayout from '@/layouts/ManageLayout';
import UserLayout from '@/layouts/UserLayout';

const SpaceListPage = lazy(() => import('@/pages/user/SpaceList'));
const JobListPage = lazy(() => import('@/pages/user/JobList'));
const TaskListPage = lazy(() => import('@/pages/user/TaskList'));
const PasswordPage = lazy(() => import('@/pages/user/Password'));

const HomePage = lazy(() => import('@/pages/host/Home'));
const AuthCallBackPage = lazy(() => import('@/pages/host/AuthCallBack'));
const DashBoardPage = lazy(() => import('@/pages/host/DashBoard'));
const SpaceCreatePage = lazy(() => import('@/pages/host/SpaceCreate'));
const SpaceModifyPage = lazy(() => import('@/pages/host/SpaceModify'));
const SpaceRecordPage = lazy(() => import('@/pages/host/SpaceRecord'));
const JobCreatePage = lazy(() => import('@/pages/host/JobCreate'));
const JobUpdatePage = lazy(() => import('@/pages/host/JobUpdate'));

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
                path: 'spaceCreate',
                element: <SpaceCreatePage />,
              },
              {
                path: ':spaceId',
                element: <DashBoardPage />,
              },
              {
                path: ':spaceId/spaceModify',
                element: <SpaceModifyPage />,
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
              // ?????? ?????? ?????? ????????? <SpaceUpdatePage/>
              // ?????? ?????? ?????? ????????? <ProfileUpdatePage />
            ],
          },
        ],
      },
    ],
  },
  {
    path: '*',
    element: <div>????????? ???????????????.</div>,
  },
];

export default routes;
