import { css } from '@emotion/react';

import theme from '@/styles/theme';

const sectionCard = css`
  display: flex;
  flex-direction: column;
  max-width: 354px;
  width: 100%;
  margin: 16px 0;
  padding: 16px;
  border: 1px solid ${theme.colors.shadow30};
  border-radius: 16px;
  box-shadow: 2px 2px 2px 0px ${theme.colors.shadow30};
  background-color: ${theme.colors.white};
`;

const locationHeader = css`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 8px;
  min-height: 48px;
`;

const locationName = css`
  font-size: 24px;
  font-weight: 600;
  margin: 0;
`;

const locationHeaderRightItems = css`
  display: flex;
  gap: 10px;
`;

const sectionAllCheckButton = css`
  width: 40px;
  height: 40px;
  margin: 0;
  padding: 0;
  box-shadow: 2px 2px 2px 0px ${theme.colors.shadow30};
`;

const task = css`
  display: flex;
  gap: 16px;
  padding: 12px 0;
`;

const textWrapper = css`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 100%;

  position: relative;

  font-weight: 500;
`;

const icon = css`
  color: ${theme.colors.gray500};
  margin-left: 16px;
  cursor: pointer;
`;

const styles = {
  sectionCard,
  task,
  icon,
  textWrapper,
  locationHeader,
  locationName,
  locationHeaderRightItems,
  sectionAllCheckButton,
};

export default styles;
