$(document).ready(function (){
    $('#compare').click(function () {

        $('#tissueCanvas').remove();
        $('#divCanvas').append('<canvas id="tissueCanvas" width="500"'
                               +'height="400" style="border:3px solid white;"></canvas>'
                               );
        let tissueCanvas = document.getElementById("tissueCanvas");

        let tissueOneSize = $('#tissuesOne').find(":selected").val();
        let tissueTwoSize = $('#tissuesTwo').find(":selected").val();
        let sum = +tissueOneSize + +tissueTwoSize;
        let percentOne =+tissueOneSize / +sum;
        let percentTwo =+tissueTwoSize / +sum;
        let maxHeight = 300;
        let luminosityHeightOne = +maxHeight * +percentOne;
        let luminosityHeightTwo = +maxHeight * +percentTwo;

        let ctxTissueOne = tissueCanvas.getContext("2d");
        ctxTissueOne.fillStyle = "#c6f212";
        ctxTissueOne.fillRect(80,(+400 - +luminosityHeightOne),130,luminosityHeightOne);
        ctxTissueOne.stroke();

        let ctxTissueTwo = tissueCanvas.getContext("2d");
        ctxTissueTwo.fillStyle = "#2449fc";
        ctxTissueTwo.fillRect(290,(+400 - +luminosityHeightTwo),130,luminosityHeightTwo);
        ctxTissueTwo.stroke();
    });
});

