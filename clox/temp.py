def getValidMoves(board, src, turn):
    row = src[0]
    col = src[1]
    if "K" in board[row][col]:
        return getUpMoves(board, src, turn) + getDownMoves(board, src, turn)
    if turn == 'R':
        return getDownMoves(board, src, turn)
    else:
        return getUpMoves(board,src, turn)
