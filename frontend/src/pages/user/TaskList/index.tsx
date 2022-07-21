import useTaskList from './useTaskList';
import { IoIosArrowBack } from 'react-icons/io';

import Button from '@/components/common/Button';
import TaskCard from '@/components/user/TaskCard';

import useSectionCheck from '@/hooks/useSectionCheck';

import { DEFAULT_IMAGE } from '@/constants/user';

import styles from './styles';

const TaskList: React.FC = () => {
  const { sectionsData, spaceData, getSections, onClickButton, goPreviousPage } = useTaskList();

  if (!sectionsData) return <></>;

  const { totalCount, checkCount, percent, isAllChecked } = useSectionCheck(sectionsData.sections);

  return (
    <div css={styles.layout}>
      <div css={styles.header}>
        <div css={styles.arrowBackIconWrapper}>
          <IoIosArrowBack size={30} onClick={goPreviousPage} />
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
        <form css={styles.form}>
          {sectionsData.sections.length === 0 ? (
            <div>체크리스트가 없습니다.</div>
          ) : (
            sectionsData.sections.map(section => (
              <section css={styles.location} key={section.id}>
                <p css={styles.locationName}>{section.name}</p>
                <TaskCard tasks={section.tasks} getSections={getSections} />
              </section>
            ))
          )}
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
