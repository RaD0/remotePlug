<table class="table table-bordered table-striped table-hover ">
    <tr>
        <th>Name</th>
        <th>Option</th>
    </tr>

    <#list root.getChildren() as item>
        <tr>
            <td>
                ${item.getRaw().getName()}

            </td>
            <td>
                <#assign handlingData = item.getHandlingData()>
                <#if helper.isAFile(item)>
                    <a href="/item/${item.getId()}">
                        <i class="${helper.getIcon(item.getFormat(), 3)}"></i>
                    </a>
                 <#else>
                     <a href="/dir/${item.getId()}">
                        <i class="${helper.getIcon(item.getFormat(), 3)}"></i>
                     </a>
                </#if>
            </td>
        </tr>
    </#list>
</table>