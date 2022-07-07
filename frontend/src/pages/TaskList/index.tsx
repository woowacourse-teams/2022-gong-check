/**  @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import Button from '@/components/_common/Button';
import PageTitle from '@/components/_common/PageTitle';

import NameModal from '@/components/InputModal/nameModal';
import TaskCard from '@/components/TaskCard';

import useModal from '@/hooks/useModal';

import apis from '@/apis';

import theme from '@/styles/theme';

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

const isAllChecked = (sections: Array<SectionType>): boolean => {
  return sections
    .map(section => section.tasks.every(task => task.checked === true))
    .every(isChecked => isChecked === true);
};

const TaskList = () => {
  const { isShowModal, openModal, closeModal } = useModal(false);
  const { jobId } = useParams();

  const [sections, setSections] = useState<Array<SectionType>>([]);

  const getSections = async (jobId: string) => {
    const {
      data: { sections },
    } = await apis.getTasks({ jobId });

    setSections(sections);
  };

  const handleClickButton = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    openModal();
  };

  useEffect(() => {
    getSections(jobId as string);
  }, []);

  if (sections.length === 0) return;

  return (
    <div css={styles.layout}>
      <PageTitle>청소 체크리스트</PageTitle>
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
          <Button
            type="submit"
            css={css`
              margin-bottom: 0;
              width: 256px;
              background: ${isAllChecked(sections) ? theme.colors.primary : theme.colors.gray};
            `}
            onClick={handleClickButton}
            disabled={!isAllChecked(sections)}
          >
            제출
          </Button>
        </form>
      </div>
      {isShowModal && (
        <NameModal
          title="체크리스트 제출"
          detail="확인 버튼을 누르면 제출됩니다."
          placeholder="이름을 입력해주세요."
          buttonText="확인"
          closeModal={closeModal}
          requiredSubmit={true}
          jobId={jobId}
        />
      )}
    </div>
  );
};

export default TaskList;
