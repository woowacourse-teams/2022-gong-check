import useJobCreate from './useJobCreate';
import { css } from '@emotion/react';

import ToggleSwitch from '@/components/common/ToggleSwitch';
import useToggleSwitch from '@/components/common/ToggleSwitch/useToggleSwitch';
import JobControl from '@/components/host/JobControl';
import MapEditor from '@/components/host/MapEditor';
import SectionCard from '@/components/host/SectionCard';

import styles from './styles';

const JobCreate: React.FC = () => {
  const { sections, createSection, newJobName, onChangeJobName, onClickCreateNewJob } = useJobCreate();
  const { toggle, onClickToggle } = useToggleSwitch();

  return (
    <div css={styles.layout}>
      <form css={styles.contents} onSubmit={onClickCreateNewJob}>
        <JobControl mode="create" jobName={newJobName} onChangeJobName={onChangeJobName} />
        <div
          css={css`
            width: 100%;
            height: 56px;
            display: flex;
            justify-content: center;
            align-items: center;
          `}
        >
          <ToggleSwitch toggle={toggle} onClickSwitch={onClickToggle} left="일반" right="도면" />
        </div>
        {!toggle ? (
          <div css={styles.grid}>
            {sections.map((section, sectionIndex) => (
              <SectionCard section={section} sectionIndex={sectionIndex} key={section.id} />
            ))}
            <div css={styles.createCard} onClick={createSection}>
              +
            </div>
          </div>
        ) : (
          <MapEditor />
        )}
      </form>
    </div>
  );
};
export default JobCreate;
