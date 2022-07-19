import { css } from '@emotion/react';
import { useMemo } from 'react';
import { IoIosArrowBack } from 'react-icons/io';
import { useQuery } from 'react-query';
import { useParams } from 'react-router-dom';

import Button from '@/components/_common/Button';

import NameModal from '@/components/NameModal';
import TaskCard from '@/components/TaskCard';

import useGoPreviousPage from '@/hooks/useGoPreviousPage';
import useModal from '@/hooks/useModal';

import apis from '@/apis';

import theme from '@/styles/theme';

import styles from './styles';

const SPACE_DATA2 = {
  name: '잠실 캠퍼스',
  imageUrl: 'https://velog.velcdn.com/images/cks3066/post/258f92c1-32be-4acb-be30-1eb64635c013/image.jpg ',
};

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

const isAllChecked = (sections: SectionType[]): boolean => {
  return sections
    .map(section => section.tasks.every(task => task.checked === true))
    .every(isChecked => isChecked === true);
};

const TaskList: React.FC = () => {
  const { openModal } = useModal();
  const { jobId } = useParams();
  const { goPreviousPage } = useGoPreviousPage();

  const { data, refetch: getSections } = useQuery(['sections', jobId], () => apis.getTasks({ jobId }), {
    suspense: true,
  });

  const handleClickButton = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    openModal(
      <NameModal
        title="체크리스트 제출"
        detail="확인 버튼을 누르면 제출됩니다."
        placeholder="이름을 입력해주세요."
        buttonText="확인"
        jobId={jobId}
      />
    );
  };

  if (!data?.sections.length) return <div>체크리스트가 없습니다.</div>;

  const { sections } = data;
  const tasks = useMemo(() => sections.map(section => section.tasks.map(task => task.checked)), [sections]);
  const checkList = useMemo(
    () =>
      tasks.reduce((prev, curv) => {
        return prev.concat(...curv);
      }, []),
    [tasks]
  );
  const totalCount = useMemo(() => checkList.length, [checkList]);
  const checkCount = useMemo(() => checkList.filter(check => check === true).length, [checkList]);
  const percent = useMemo(() => Math.ceil((checkCount / totalCount) * 100), [checkCount, totalCount]);

  return (
    <div css={styles.layout}>
      <div css={styles.header}>
        <div css={styles.arrowBackIconWrapper}>
          <IoIosArrowBack
            css={css`
              color: black;
              cursor: pointer;
            `}
            size={30}
            onClick={goPreviousPage}
          />
        </div>
        <div css={styles.thumbnail(SPACE_DATA2.imageUrl)} />
        <div css={styles.infoWrapper}>
          <p>잠실 캠퍼스</p>
          <p>청소 체크리스트</p>
        </div>
      </div>
      <div css={styles.progressBarWrapper}>
        <div css={styles.progressBar(percent)} />
        <span css={styles.percentText}>{`${checkCount}/${totalCount}`}</span>
      </div>
      <div css={styles.contents}>
        <form
          css={css`
            display: flex;
            flex-direction: column;
            align-items: center;
          `}
        >
          {data.sections.map(({ id, name, tasks }) => (
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
              background: ${isAllChecked(data.sections) ? theme.colors.primary : theme.colors.gray400};
            `}
            onClick={handleClickButton}
            disabled={!isAllChecked(data.sections)}
          >
            제출
          </Button>
        </form>
      </div>
    </div>
  );
};
export default TaskList;
