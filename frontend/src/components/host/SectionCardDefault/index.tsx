import useSectionCardDefault from './useSectionCardDefault';

import Button from '@/components/common/Button';
import TaskBox from '@/components/host/TaskBox';

import { SectionType } from '@/types';

import styles from './styles';

interface SectionCardDefaultProps {
  section: SectionType;
  sectionIndex: number;
}

const SectionCardDefault: React.FC<SectionCardDefaultProps> = ({ section, sectionIndex }) => {
  const { sectionName, isEditing, inputRef, onClickEdit, onSubmit, onChange, onClickDelete, onClickCreate } =
    useSectionCardDefault(section, sectionIndex);

  return (
    <>
      <form css={styles.titleWrapper} onSubmit={onSubmit}>
        {isEditing ? (
          <>
            <input css={styles.input} ref={inputRef} value={sectionName} onChange={onChange} />
            <Button type="submit" css={styles.confirmButton}>
              확인
            </Button>
          </>
        ) : (
          <>
            <span onClick={onClickEdit}>{sectionName}</span>
            <div>
              <Button type="button" css={styles.editButton} onClick={onClickEdit}>
                이름 수정
              </Button>
              <Button type="button" css={styles.deleteButton} onClick={onClickDelete}>
                삭제
              </Button>
            </div>
          </>
        )}
      </form>
      {section.tasks.map((task, taskIndex) => (
        <TaskBox task={task} taskIndex={taskIndex} sectionIndex={sectionIndex} key={task.id} />
      ))}
      <button css={styles.newTaskButton} onClick={onClickCreate}>
        + 새 작업 추가
      </button>
    </>
  );
};

export default SectionCardDefault;
