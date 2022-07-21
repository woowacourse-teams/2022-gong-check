import useTaskList from './useTaskList';
import { css } from '@emotion/react';
import { IoIosArrowBack } from 'react-icons/io';

import Button from '@/components/common/Button';
import TaskCard from '@/components/user/TaskCard';

import { DEFAULT_IMAGE } from '@/constants/user';

import theme from '@/styles/theme';

import styles from './styles';

const TaskList: React.FC = () => {
  const {
    data,
    spaceData,
    getSections,
    onClickButton,
    goPreviousPage,
    totalCount,
    checkCount,
    percent,
    isNotData,
    isAllChecked,
  } = useTaskList();

  if (isNotData) return <div>체크리스트가 없습니다.</div>;

  return (
    <div css={styles.layout}>
      <div css={styles.header}>
        <div css={styles.arrowBackIconWrapper}>
          <IoIosArrowBack
            css={css`
              color: ${theme.colors.black};
              cursor: pointer;
            `}
            size={30}
            onClick={goPreviousPage}
          />
        </div>
        <div css={styles.thumbnail(spaceData?.imageUrl || DEFAULT_IMAGE)} />
        <div css={styles.infoWrapper}>
          <p>잠실 캠퍼스</p>
          <p>청소 체크리스트</p>
        </div>
      </div>
      <div css={styles.progressBarWrapper}>
        <div css={styles.progressBar(percent || 0)} />
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
          {data?.sections.map(section => (
            <section css={styles.location} key={section.id}>
              <p css={styles.locationName}>{section.name}</p>
              <TaskCard tasks={section.tasks} getSections={getSections} />
            </section>
          ))}
          <Button
            type="submit"
            css={styles.button(isAllChecked || false)}
            onClick={onClickButton}
            disabled={!isAllChecked}
          >
            제출
          </Button>
        </form>
      </div>
    </div>
  );
};
export default TaskList;
