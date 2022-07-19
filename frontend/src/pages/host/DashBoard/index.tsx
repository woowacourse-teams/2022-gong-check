import submissions from '@/mock/submissions';
import { css } from '@emotion/react';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import JobListCard from '@/components/host/JobListCard';
import SpaceInfo from '@/components/host/SpaceInfo';
import Submissions from '@/components/host/Submissions';

import styles from './styles';

const DashBoard: React.FC = () => {
  const navigate = useNavigate();
  const { spaceId } = useParams();

  // TODO: spaceId에 해당하는 각 컴포넌트 렌더링

  // const { data } = useQuery(['space', spaceId], () => apis.getSpace({ spaceId }), { suspense: true });
  // 단건 조회로 해당 데이터를 활용해 아래 컴포넌트들을 렌더링 한다.

  // SpaceInfo 공간정보 수정하기 구현 해야 합니다.
  const onClickSubmissionsDetail = () => {
    navigate('spaceRecord');
  };

  return (
    <div css={styles.layout}>
      <h1>공간 관리</h1>
      <div css={styles.contents}>
        <div css={styles.cardWrapper}>
          <SpaceInfo isEditMode={false} data={mockData} />
          <JobListCard />
        </div>
        <Submissions submissions={submissions} onClick={onClickSubmissionsDetail} />
      </div>
    </div>
  );
};

export default DashBoard;
