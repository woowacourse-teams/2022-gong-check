import submissions from '@/mock/submissions';
import { css } from '@emotion/react';
import { useNavigate, useParams } from 'react-router-dom';

import JobList from '@/components/host/JobList';
import SpaceInfo from '@/components/host/SpaceInfo';
import Submissions from '@/components/host/Submissions';

const DashBoard: React.FC = () => {
  const navigate = useNavigate();
  const { spaceId } = useParams();
  // TODO: spaceId에 해당하는 각 컴포넌트 렌더링
  // const { data } = useQuery(단건조회, { suspense: true });
  // 단건 조회로 해당 데이터를 활용해 아래 컴포넌트들을 렌더링 한다.

  // SpaceInfo 공간정보 수정하기 구현 해야 합니다.
  const onClickSubmissionsDetail = () => {
    navigate(`/host/manage/spaceRecord/${spaceId}`);
  };

  return (
    <div
      css={css`
        padding: 16px 48px;

        /* 임시 css 설정 - start*/
        display: flex;
        flex-direction: column;
        gap: 20px;
        /* 임시 css 설정 - end*/
      `}
    >
      <h1>공간 관리</h1>
      <SpaceInfo />
      <JobList />
      <Submissions submissions={submissions} onClick={onClickSubmissionsDetail} />
    </div>
  );
};

export default DashBoard;
