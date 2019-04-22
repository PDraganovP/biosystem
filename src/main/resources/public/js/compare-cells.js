$(document).ready(function (){
    $('#compare').click(function () {

        $('#cellCanvas').remove();
        $('#divCanvas').append('<canvas id="cellCanvas" width="500"'
                               +'height="400" style="border:3px solid white;"></canvas>'
                               );
        let cellCanvas = document.getElementById("cellCanvas");

        let cellOneSize = $('#cellsOne').find(":selected").val();
        let cellTwoSize = $('#cellsTwo').find(":selected").val();
        let sum = +cellOneSize + +cellTwoSize;
        let percentOne =+cellOneSize / +sum;
        let percentTwo =+cellTwoSize / +sum;
        let maxHeight = 300;
        let luminosityHeightOne = +maxHeight * +percentOne;
        let luminosityHeightTwo = +maxHeight * +percentTwo;

        let ctxCellOne = cellCanvas.getContext("2d");
        ctxCellOne.fillStyle = "#c6f212";
        ctxCellOne.fillRect(80,(+400 - +luminosityHeightOne),130,luminosityHeightOne);
        ctxCellOne.stroke();

        let ctxCellTwo = cellCanvas.getContext("2d");
        ctxCellTwo.fillStyle = "#2449fc";
        ctxCellTwo.fillRect(290,(+400 - +luminosityHeightTwo),130,luminosityHeightTwo);
        ctxCellTwo.stroke();

    });
});

