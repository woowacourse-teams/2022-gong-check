import { css } from '@emotion/react';

const layout = (isManagePath: boolean) => css`
  display: flex;
  flex-direction: column;
  width: 100vw;
  height: 100vh;
  background-color: #f9faff;
  padding-left: ${isManagePath ? '224px' : '0'};
`;

const styles = { layout };

export default styles;
