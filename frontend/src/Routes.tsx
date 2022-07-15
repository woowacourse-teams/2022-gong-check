import { lazy } from 'react';

import DefaultLayout from '@/layouts/DefaultLayout';
import HostLayout from '@/layouts/HostLayout';
import ManageLayout from '@/layouts/ManageLayout';
import UserLayout from '@/layouts/UserLayout';

const SpaceListPage = lazy(() => import('@/pages/user/SpaceList'));
const JobListPage = lazy(() => import('@/pages/user/JobList'));
const TaskListPage = lazy(() => import('@/pages/user/TaskList'));

const HomePage = lazy(() => import('@/pages/host/Home'));
const LoginPage = lazy(() => import('@/pages/host/Login'));
const AuthCallBackPage = lazy(() => import('@/pages/host/AuthCallBack'));
const SpaceCreatePage = lazy(() => import('@/pages/host/SpaceCreate'));

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
            path: '',
            element: <HomePage />,
          },
          {
            path: 'login',
            element: <LoginPage />,
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
            ],
            // children: [
            // 공간 생성, 수정 <SpaceCreatePage/>
            // 공간 정보 수정 페이지 <SpaceUpdatePage/>
            // 전체 공간 정보 보여주기 <DashBoardPage/>
            // 공간 사용 내역 보기 페이지 <SpaceRecordPage/>
            // 체크리스트 생성 페이지 <JobCreatePage />
            // 체크리스트 수정 페이지 <JobUpdatePage />
            // 회원 정보 수정 페이지 <JobUpdatePage />
            // ]
          },
        ],
      },
    ],
  },
];

export default routes;
