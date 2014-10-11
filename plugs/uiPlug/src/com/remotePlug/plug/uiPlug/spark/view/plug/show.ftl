<#include "/wraps/header.ftl">
<#include "/wraps/menu.ftl">

<style>

    div#possible-operations {
        border: 2px solid black;
        margin-top: 5px;
        padding: 6px;
    }

</style>


<div class="container">
    <#include "/partials/currently_running.ftl">
    <#include "/partials/back.ftl">
    <div class="row">
        <div class="col-lg-12 text-center">
            <h4 style="word-wrap: break-word;">${item.getRaw().getName()}</h4>
        </div>
    </div>

    <div class="row my-spl-row">
        <div class="col-lg-12 text-center">
            <h1></h1>
            <div class="row">
                <div class="panel panel-primary">
                    <div class="panel-heading"></div>
                    <div class="panel-body">
                        <#assign handlingData = item.getHandlingData() >
                        <div id="operation-main">
                            <a href="/item/${item.getId()}/${handlingData.getStartOperation()}">
                                <i class="${helper.getIcon(handlingData.getStartOperation(), 5)}"></i>
                            </a>
                        </div>

                        <div id="possible-operations">
                            <#list handlingData.getPermittedOperations() as operation>
                                <a href="/item/${item.getId()}/${operation}">
                                    <button class="btn btn-lg btn-default">
                                        ${operation}
                                    </button>
                                </a>
                            </#list>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<#include "/wraps/footer.ftl">