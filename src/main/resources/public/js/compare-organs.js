$(document).ready(function (){
    $('#compare').click(function () {

        $('#organCanvas').remove();
        $('#divCanvas').append('<canvas id="organCanvas" width="500"'
                               +'height="400" style="border:3px solid white;"></canvas>'
                               );
        let organCanvas = document.getElementById("organCanvas");

        let organOneSize = $('#organsOne').find(":selected").val();
        let organTwoSize = $('#organsTwo').find(":selected").val();
        let sum = +organOneSize + +organTwoSize;
        let percentOne =+organOneSize / +sum;
        let percentTwo =+organTwoSize / +sum;
        let maxHeight = 300;
        let luminosityHeightOne = +maxHeight * +percentOne;
        let luminosityHeightTwo = +maxHeight * +percentTwo;

        let ctxOrganOne = organCanvas.getContext("2d");
        ctxOrganOne.fillStyle = "#c6f212";
        ctxOrganOne.fillRect(80,(+400 - +luminosityHeightOne),130,luminosityHeightOne);
        ctxOrganOne.stroke();

        let ctxOrganTwo = organCanvas.getContext("2d");
        ctxOrganTwo.fillStyle = "#2449fc";
        ctxOrganTwo.fillRect(290,(+400 - +luminosityHeightTwo),130,luminosityHeightTwo);
        ctxOrganTwo.stroke();
    });
});

