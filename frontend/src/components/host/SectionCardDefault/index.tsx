import useSectionCardDefault from './useSectionCardDefault';
import { BiTrash } from 'react-icons/bi';

import Button from '@/components/common/Button';
import TaskBox from '@/components/host/TaskBox';

import { SectionType } from '@/types';

import styles from './styles';

interface SectionCardDefaultProps {
  section: SectionType;
  sectionIndex: number;
}

const SectionCardDefault: React.FC<SectionCardDefaultProps> = ({ section, sectionIndex }) => {
  const { onChange, onClickDelete, onClickCreate } = useSectionCardDefault(sectionIndex);

  return (
    <>
      <div css={styles.titleWrapper}>
        <input
          css={styles.input}
          placeholder="새 구역"
          defaultValue={section.name}
          maxLength={10}
          onChange={onChange}
          required
        />
        <BiTrash css={styles.deleteButton} size={26} onClick={onClickDelete} />
      </div>
      {section.tasks.map((task, taskIndex) => (
        <TaskBox task={task} taskIndex={taskIndex} sectionIndex={sectionIndex} key={task.id} />
      ))}
      <button css={styles.newTaskButton} onClick={onClickCreate} type="button">
        + 새 작업 추가
      </button>
    </>
  );
};

export default SectionCardDefault;
