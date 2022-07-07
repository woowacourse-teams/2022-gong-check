/**  @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useEffect, useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';

import Button from '@/components/_common/Button';
import PageTitle from '@/components/_common/PageTitle';

import InputModal from '@/components/InputModal';
import TaskCard from '@/components/TaskCard';

import useModal from '@/hooks/useModal';

import apis from '@/apis';

import styles from './styles';

type SectionType = {
  id: number;
  name: string;
  tasks: Array<TaskType>;
};

type TaskType = {
  id: number;
  name: string;
  checked: boolean;
};

type LocationStateType = {
  active: boolean;
};

const TaskList = () => {
  const { isShowModal, closeModal } = useModal(false);
  const { state } = useLocation();
  const { active } = state as LocationStateType;

  const { jobId } = useParams();

  const [sections, setSections] = useState<Array<SectionType>>([]);

  const getSections = async (jobId: string) => {
    const {
      data: { sections },
    } = await apis.getTasks({ jobId });

    setSections(sections);
  };

  const newTasks = async () => {
    await apis.postNewTasks({ jobId });
    await getSections(jobId as string);
  };

  useEffect(() => {
    active ? getSections(jobId as string) : newTasks();
  }, []);

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
          {sections.map(({ id, name, tasks }) => (
            <section css={styles.location} key={id}>
              <p css={styles.locationName}>{name}</p>
              <TaskCard tasks={tasks} getSections={getSections} />
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
