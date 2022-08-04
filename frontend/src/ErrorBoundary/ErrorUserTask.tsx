import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { QueryErrorResetBoundary } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import useToast from '@/hooks/useToast';

// 사용자가 체크리스트 페이지에 접속 중일 때, 관리자가 작업을 수정 할 경우 발생
const NOT_TASK_TEXT = '존재하지 않는 작업입니다.';

// 사용자들이 체크리스트 페이지에 접속 중일 때, 다른 사용자가 체크리시트를 제출 한 경우 발생
const NOT_JOB_TEXT = '작업이 존재하지 않습니다.';
const NOT_JOB_WORK = '현재 진행중인 작업이 존재하지 않아 조회할 수 없습니다.';

interface ErrorUserTaskProps {
  children: React.ReactNode;
}

const ErrorUserTask: React.FC<ErrorUserTaskProps> = ({ children }) => {
  const navigate = useNavigate();
  const { hostId } = useParams();
  const [message, setMessage] = useState<string | undefined>('');

  const { openToast } = useToast();

  useEffect(() => {
    if (message === NOT_TASK_TEXT) {
      openToast('ERROR', `관리자가 체크리스트를 수정했습니다. 공간 선택 페이지로 이동합니다.`);
      navigate(`/enter/${hostId}/spaces`);
    }

    if (message === NOT_JOB_TEXT || message === NOT_JOB_WORK) {
      openToast('ERROR', `다른 사용자가 체크리시트를 제출 했습니다. 공간 선택 페이지로 이동합니다.`);
      navigate(`/enter/${hostId}/spaces`);
    }
  }, [message]);

  return (
    <QueryErrorResetBoundary>
      <ErrorBoundary
        fallbackRender={({ error }) => {
          const err = error as AxiosError<{ message: string }>;
          const message = err.response?.data.message;

          setMessage(message);

          return <></>;
        }}
      >
        {children}
      </ErrorBoundary>
    </QueryErrorResetBoundary>
  );
};

export default ErrorUserTask;
