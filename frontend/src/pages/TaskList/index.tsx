/**  @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

import Button from '@/components/_common/Button';
import PageTitle from '@/components/_common/PageTitle';

import InputModal from '@/components/InputModal';
import TaskCard from '@/components/TaskCard';

import useModal from '@/hooks/useModal';

import styles from './styles';

const locations = [
  {
    id: 1,
    name: '대강의실',
    tasks: [
      {
        id: 1,
        name: '창문 닦기',
        isChecked: true,
      },
      {
        id: 2,
        name: '책상 닦기',
        isChecked: false,
      },
    ],
  },
  {
    id: 2,
    name: '소강의실1',
    tasks: [
      {
        id: 1,
        name: '창문 닦기',
        isChecked: false,
      },
      {
        id: 2,
        name: '창틀 닦기',
        isChecked: true,
      },
      {
        id: 3,
        name: '에어컨 끄기',
        isChecked: true,
      },
      {
        id: 4,
        name: '로비 정리',
        isChecked: true,
      },
    ],
  },
];

const TaskList = () => {
  const { isShowModal, closeModal } = useModal();
  return (
    <div css={styles.layout}>
      <PageTitle>마감 체크리스트</PageTitle>
      <div css={styles.contents}>
        <form
          css={css`
            display: flex;
            flex-direction: column;
            align-items: center;
          `}
        >
          {locations.map(location => (
            <section css={styles.location} key={location.id}>
              <p css={styles.locationName}>{location.name}</p>
              <TaskCard tasks={location.tasks} />
            </section>
          ))}
          <Button type="submit">제출</Button>
        </form>
      </div>
      {isShowModal && (
        <InputModal
          title="체크리스트 제출"
          detail="확인 버튼을 누르면 제출됩니다."
          placeholder="이름을 입력해주세요."
          buttonText="확인"
          closeModal={closeModal}
          requiredSubmit={true}
        />
      )}
    </div>
  );
};

export default TaskList;
